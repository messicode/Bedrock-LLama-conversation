### Steps to Deploy and Test

#### Set Up IAM Role for Lambda:
1. Create an IAM role with permissions to access Amazon Bedrock.
2. Attach the `AmazonBedrockFullAccess` policy.

#### Create a Lambda Function:
1. Go to AWS Lambda in the console.
2. Create a new function.
3. Use the Python runtime (e.g., Python 3.9 or later).
4. Attach the IAM role created in the previous step.

#### Upload the Lambda Code:
1. Package the `lambda_function.py` into a zip file.
2. Upload the zip file to the Lambda console.

#### Test the Function:
1. Create a test event in the Lambda console with the following content:
   ```json
   {
     "body": "{\"query\": \"Explain the CAP theorem in cloud computing.\"}"
   }
2. Invoke the function to see the generated response.
#### Integrate with API Gateway:
1. Create an API Gateway REST API.
2. Add a POST method that integrates with the Lambda function.
3. Deploy the API to a stage.
