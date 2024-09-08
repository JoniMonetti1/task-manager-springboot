package com.joni.task_manager_springboot.cli;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TaskCli {
    private static final String API_URL = "http://localhost:8080/system/api/v1/tasks";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException{
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [<args>]");
            return;
        }

        String command = args[0];
        try {
            switch (command) {
                case "list":
                    if (args.length == 1) {
                        listTasks();
                        break;
                    }
                    listTasksByStatus(args[1]);
                    break;
                case "add":
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli add <description>");
                        return;
                    }
                    addTask(args[1]);
                    break;
                case "update":
                    if (args.length < 3) {
                        System.out.println("Usage: task-cli update <id> <new description>");
                        return;
                    }
                    updateTask(args[1], args[2]);
                    break;
                case "delete":
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli delete <id>");
                        return;
                    }
                    deleteTask(args[1]);
                    break;
                case "mark-in-progress":
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli mark-in-progress <id>");
                        return;
                    }
                    markTaskInProgress(args[1]);
                    break;
                case "mark-done":
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli mark-done <id>");
                        return;
                    }
                    markTaskDone(args[1]);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while sending request: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Operation was interrupted: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
         return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void handleResponse(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        String body = response.body();

        switch (statusCode) {
            case 200:
            case 201:
                System.out.println("Operation successful. Response: " + body);
                break;
            case 204:
                System.out.println("Operation successful. No content returned.");
                break;
            case 400:
                System.out.println("Bad request. Please check your input. Details: " + body);
                break;
            case 404:
                System.out.println("Resource not found. Details: " + body);
                break;
            case 500:
                System.out.println("Server error occurred. Please try again later. Details: " + body);
                break;
            default:
                System.out.println("Unexpected response. Status: " + statusCode + ", Body: " + body);
        }
    }

    private static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    private static void listTasks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void listTasksByStatus(String status) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(API_URL + "/status/" + status))
               .GET()
               .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void addTask(String description) throws IOException, InterruptedException {
        validateNotEmpty(description, "Task description");

        String json = String.format("{\"description\":\"%s\",\"status\":\"TODO\"}", description);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void updateTask(String id, String newDescription) throws IOException, InterruptedException {
        validateNotEmpty(newDescription, "Task description");
        validateNotEmpty(id, "Task Id");

        String json = String.format("{\"description\":\"%s\"}", newDescription);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void deleteTask(String id) throws IOException, InterruptedException {
        validateNotEmpty(id, "Task Id");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void markTaskInProgress(String id) throws IOException, InterruptedException {
        validateNotEmpty(id, "Task Id");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id + "/mark-in-progress"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }

    private static void markTaskDone(String id) throws IOException, InterruptedException {
        validateNotEmpty(id, "Task Id");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id + "/mark-done"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = sendRequest(request);
        handleResponse(response);
    }
}
