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
        stage('Debug Info') {
            steps {
                echo "Using SonarQube at ${SONAR_HOST_URL}"
                echo "Pushing Docker image to ${DOCKER_REPO}"
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {

                sh 'mvn sonar:sonar -Dsonar.projectKey=Kaddem-key -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
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

        stage('Start with Docker Compose') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }

        stage('Test Application Endpoint') {
            steps {
                sh 'sleep 10'
                sh 'curl -X POST http://localhost:8080/api/example -H "Content-Type: application/json" -d \'{"key":"value"}\''
                sh 'curl http://localhost:8080/api/example'
            }
        }

        stage('Monitoring Ready') {
            steps {
                echo 'Monitoring services should now be visible on Grafana/Prometheus.'
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