# An artificial conversation: Bedrock based LLM and Ollama LLama talk (CS441-HW3)
## Name : Yashvardhan Udia
## UIN: 656090513
## NetID: yudia2@uic.edu

This project involves building and deploying a conversational agent using Akka HTTP, Scala, AWS Lambda, AWS Bedrock and the Ollama Llama model. It includes designing a RESTful API for LLM interactions (utilizing protobufs, gPRC, AWS API Gateway), deploying the server on AWS EC2, and testing the system using Docker containers.

## Overview

### Project structure

```plaintext
│
├── AkkaHttpServer
│   └── Server project files
├── Conversational Agent
│   └── Client project files
├── Lambda Function
│   └── Python Lambda function
└── .git/
```


### AkkaHttpServer
This project comtains the files for an akka-http based RESTful API acting as a server between the locally hosted Ollama LLama model and AWS Bedrock model.

### Conversational Agent
This project uses Akka HTTP to send a query to a server, then processes the response with a locally run instance Ollama API to generate a follow-up query in a loop. It repeats this process for a specified number of iterations (5 for now), simulating a conversation.

### Lambda Function
This has a Python file for an AWS Lambda function that processes LLM queries using Amazon Bedrock for text generation. The Lambda function integrates with Bedrock to handle the generation logic. It is triggered only when a specific event occurs.


## Building and Running

### Requirements

- Scala 2.12.20
- Ollama4j 1.0
- Akka-http 10.5.3
- SBT (Scala Build Tool) version (1.10.1)
- AWS Lambda, Bedrock core Java/SDK libraries
- gRPC 1.60.0
- Protobuf java 3.19.0
- ScalaPB

## Running

1) Deploy the conversational agent and Ollama server configured to run LLama3.2, locally.
2) Deploy the Akka-http server on AWS ECR and configure the url:port.
3) Run the AWS Lambda funtion and make sure the communication is taking place at the correct addresses.
4) Use Postman to send the intial client signal to the http server in EC2, which will trigger the lambda function generating a response from the AWS Bedrock.
5) This response from Bedrock will be sent by server to ollama client as a query, which will respond to it using LLama, and the loop will go on.


## Contributing
Contributions are welcome! Please fork the repository and submit pull requests with any enhancements. Ensure to add unit tests for new features.

## License

This project is licensed under the [MIT License](https://github.com/messicode/Distributed_Systems/blob/master/LICENSE.txt). Feel free to use, modify, and distribute it as per the license terms.

## YOUTUBE DEMO (SOON)
[My video](https://youtu.be/HhFxq8NGAGk)


## NOTE
- This project was successfully run on Windows 10 using command line
