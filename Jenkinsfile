def sendSuccessEmail() {
    mail to: "${env.ADMIN_MAIL_ID}",
         subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
         body: "Good news! The build and deployment of ${env.JOB_NAME} #${env.BUILD_NUMBER} was successful."
}

def sendFailureEmail() {
    mail to: "${env.ADMIN_MAIL_ID}",
         subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
         body: "Unfortunately, the build and deployment of ${env.JOB_NAME} #${env.BUILD_NUMBER} failed. Please check the Jenkins console output for more details."
}

pipeline {

    agent any

    tools {
        maven 'Maven-3.8'
        jdk 'JDK17'
    }

    environment {
        MAVEN_HOME = tool 'Maven-3.8'
        JAVA_HOME = tool 'JDK17'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                // Checkout the code from the Git repository
                 git url: "${env.GIT_URL}", branch: "${env.BRANCH_NAME}"
            }
        }

        stage('Compile') {
            steps {
                // Compile the code
                bat 'mvn compile'
            }
        }

        stage('Run Tests') {
            steps {
                // Run unit tests
                bat 'mvn test'
            }
            post {
                always {
                    // Archive test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

		stage('Static Code Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('Sonarqube') {
                    bat 'mvn verify sonar:sonar'
                }
            }
        }

        stage('Build') {
            steps {
                // Build the Spring Boot application and create a package
                bat 'mvn package'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image
                bat "docker build -t ${env.DOCKER_USERNAME}/devops-integration ."
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push the Docker image to Docker Hub

					withCredentials([string(credentialsId: 'DOCKERHUB_PWD', variable: 'DOCKERHUB_PASSWORD')]) {
					    bat "docker login -u ${env.DOCKER_USERNAME} -p %DOCKERHUB_PASSWORD%"
					}
                    bat "docker push ${env.DOCKER_USERNAME}/devops-integration"
                }
            }
        }
    }

	post {
        always {
            // Clean up workspace after the build
            cleanWs()
        }
        success {
            sendSuccessEmail()
        }
        failure {
            sendFailureEmail()
        }
    }
}
