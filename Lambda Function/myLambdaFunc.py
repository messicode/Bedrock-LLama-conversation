import boto3
import json
import logging

# Set up logging
logger = logging.getLogger()
logger.setLevel(logging.INFO)

# Initialize the Bedrock client
bedrock_client = boto3.client('bedrock-runtime', region_name='us-east-1')  # Replace with your Bedrock region

# Lambda function handler
def lambda_handler(event, context):
    """
    Lambda function to process LLM queries using Amazon Bedrock.
    """
    try:
        # Parse input query
        body = json.loads(event['body'])
        query = body.get("query", "")
        if not query:
            return {
                'statusCode': 400,
                'body': json.dumps({"error": "Query not provided"})
            }

        logger.info(f"Processing query: {query}")

        # Define Bedrock model and request payload
        model_id = "amazon.titan-tg1-large"  # Use a Bedrock model, replace if needed
        payload = {
            "inputText": query,
            "parameters": {
                "maxTokens": 200,  # Adjust token length as required
                "temperature": 0.7,  # Creativity of response
                "topP": 0.9  # Probability threshold for sampling
            }
        }

        # Call Bedrock for text generation
        response = bedrock_client.invoke_model(
            modelId=model_id,
            contentType="application/json",
            accept="application/json",
            body=json.dumps(payload)
        )

        # Parse response
        response_body = json.loads(response['body'])
        generated_text = response_body.get("generatedText", "")

        logger.info(f"Generated text: {generated_text}")

        # Return successful response
        return {
            'statusCode': 200,
            'body': json.dumps({"response": generated_text})
        }

    except Exception as e:
        logger.error(f"Error processing query: {e}")
        return {
            'statusCode': 500,
            'body': json.dumps({"error": str(e)})
        }
