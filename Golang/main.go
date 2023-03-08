package main

import (
	"context"
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/sessions"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/google"
)

var (
	googleOauthConfig *oauth2.Config
	store             = sessions.NewCookieStore([]byte("your-secret-key"))
	db                *sql.DB
)

func init() {
	googleOauthConfig = &oauth2.Config{
		RedirectURL:  "http://localhost:3000/auth/google/callback",
		ClientID:     os.Getenv("GOOGLE_CLIENT_ID"),
		ClientSecret: os.Getenv("GOOGLE_SECRET_KEY"),
		Scopes: []string{
			"https://www.googleapis.com/auth/userinfo.email",
		},
		Endpoint: google.Endpoint,
	}

	var err error
	db, err = sql.Open("postgres", "postgresql://user:password@localhost/dbname?sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
}

func handleGoogleLogin(w http.ResponseWriter, r *http.Request) {
	state := "random-state-string"
	url := googleOauthConfig.AuthCodeURL(state)
	http.Redirect(w, r, url, http.StatusTemporaryRedirect)

	session, _ := store.Get(r, "session-name")
	session.Values["state"] = state
	session.Save(r, w)
}

func handleGoogleCallback(w http.ResponseWriter, r *http.Request) {
	code := r.URL.Query().Get("code")
	session, _ := store.Get(r, "session-name")
	state := session.Values["state"].(string)
	token, err := googleOauthConfig.Exchange(context.Background(), code)
	if err != nil {
		fmt.Fprintf(w, "Failed to exchange token: %s", err.Error())
		return
	}

	_, err = db.Exec(`
        INSERT INTO oauthstate (state, session_id)
        VALUES ($1, $2)
    `, state, session.ID)
	if err != nil {
		if pqErr, ok := err.(*pq.Error); ok && pqErr.Code.Name() == "unique_violation" {
			// Do nothing
		} else {
			log.Println(err)
			http.Error(w, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)
			return
		}
	}

	session.Values["token"] = token.AccessToken
	session.Save(r, w)

	fmt.Fprintf(w, "Token: %+v", token)
}

func handleProtected(w http.ResponseWriter, r *http.Request) {
	session, _ := store.Get(r, "session-name")
	token := session.Values["token"]
	if token == nil {
		http.Redirect(w, r, "/", http.StatusSeeOther)
		return
	}
	fmt.Fprintf(w, "Token: %s", token)
}

func main() {
	http.HandleFunc("/login", handleGoogleLogin)
	http.HandleFunc("/auth/google/callback", handleGoogleCallback)
	http.HandleFunc("/protected", handleProtected)

	fmt.Println("Server is running on port 3000")
	if err := http.ListenAndServe(":3000", nil); err != nil {
		panic(err)
	}
}
