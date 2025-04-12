pipeline {
    agent {
            label 'agent'
        }

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REPO = 'docker.io/nadabkh'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                script {
                    sh 'mvn deploy'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def appName = 'myapp'
                    def imageTag = "${DOCKER_REPO}/${appName}:latest"

                    sh "docker build -t ${appName}:latest ."

                    sh "docker tag ${appName}:latest ${imageTag}"

                    sh "docker push ${imageTag}"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
