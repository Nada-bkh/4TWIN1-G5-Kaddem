pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REPO = 'docker.io/nadabkh'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('maven:3.8.8-openjdk-17').inside('-v /root/.m2:/root/.m2') {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    docker.image('maven:3.8.8-openjdk-17').inside('-v /root/.m2:/root/.m2') {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn sonar:sonar'
                        }
                    }
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                script {
                    docker.image('maven:3.8.8-openjdk-17').inside('-v /root/.m2:/root/.m2') {
                        sh 'mvn deploy'
                    }
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
}
