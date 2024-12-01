# An artificial conversation: Bedrock based LLM and Ollama LLama talk (CS441-HW3)
## Name : Yashvardhan Udia
## UIN: 656090513
## NetID: yudia2@uic.edu

This project involves building and deploying a conversational agent using Akka HTTP, Scala, AWS Lambda, AWS Bedrock and the Ollama Llama model. It includes designing a RESTful API for LLM interactions (utilizing protobufs, gPRC, AWS API Gateway), deploying the server on AWS EC2, and testing the system using Docker containers.

# Overview

## AkkaHttpServer
This project comtains the files for an akka-http based RESTful API acting as a server between the locally hosted Ollama LLama model and AWS Bedrock model.

## Conversational Agent
This project uses Akka HTTP to send a query to a server, then processes the response with a locally run instance Ollama API to generate a follow-up query in a loop. It repeats this process for a specified number of iterations (5 for now), simulating a conversation.

## Lambda Function
This has a Python file for an AWS Lambda function that processes LLM queries using Amazon Bedrock for text generation. The Lambda function integrates with Bedrock to handle the generation logic. It is triggered only when a specific event occurs.


