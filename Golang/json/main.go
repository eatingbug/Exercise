package main

import (
	"encoding/json"
	"fmt"
)

type Course struct {
	Place []struct {
		PlaceName string `json:"place_name"`
		Address   string `json:"address"`
	} `json:"course[0]"`
}

func main() {
	// Simulate an incoming JSON request
	jsonRequest := `{
        "course[0]": [
            {
                "place_name": "CGV 청담씨네시티",
                "address": "서울 강남 청담동 블라블라"
            },
            {
                "place_name": "온달집 본점",
                "address": "서울 강남 신사동 블라블라"
            }
        ]
    }`

	// Unmarshal the JSON request into a Course struct
	var course Course
	err := json.Unmarshal([]byte(jsonRequest), &course)
	if err != nil {
		fmt.Println("Error unmarshaling JSON:", err)
		return
	}

	// Marshal the Course struct into a JSON string
	jsonString, err := json.Marshal(course)
	if err != nil {
		fmt.Println("Error marshaling JSON:", err)
		return
	}

	// Print the JSON string
	fmt.Println(string(jsonString))

	// Store the JSON string in the database
	// ...
}
