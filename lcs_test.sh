#!/bin/bash

# Request host and port input
echo "Enter the server host (e.g., localhost):"
read host
echo "Enter the server port (e.g., 8080):"
read port

# Define the URL
url="http://${host}:${port}/lcs"

# Request user input for the entire JSON body
echo "Enter the entire JSON body:"
read json_body

# Send the request
response=$(curl -s -X POST -H "Content-Type: application/json" -d "$json_body" "$url")

# Print the response
echo "Response from server:"
echo "$response"
