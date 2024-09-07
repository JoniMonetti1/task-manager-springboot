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
    }

    private static void sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void listTasks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();
        sendRequest(request);
    }

    private static void listTasksByStatus(String status) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(API_URL + "/status/" + status))
               .GET()
               .build();
        sendRequest(request);
    }

    private static void addTask(String description) throws IOException, InterruptedException {
        String json = String.format("{\"description\":\"%s\",\"status\":\"TODO\"}", description);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        sendRequest(request);
    }

    private static void updateTask(String id, String newDescription) throws IOException, InterruptedException {
        String json = String.format("{\"description\":\"%s\"}", newDescription);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        sendRequest(request);
    }

    private static void deleteTask(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .DELETE()
                .build();
        sendRequest(request);
    }

    private static void markTaskInProgress(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id + "/mark-in-progress"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        sendRequest(request);
    }

    private static void markTaskDone(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id + "/mark-done"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        sendRequest(request);
    }
}
