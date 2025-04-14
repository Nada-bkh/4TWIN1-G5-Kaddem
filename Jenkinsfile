pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = 'benjdidiahabib-g5-kaddem'
    }

    stages {

        stage('Checkout Source Code') {
            steps {
                git branch: 'BenJdidiaHabib-4TWIN1-G5', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }

        stage('Build & Unit Tests') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }

        stage('Deploy JAR to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }

        stage('API Tests') {
            steps {
                sh '''
                sleep 10
                curl -X POST http://localhost:8089/kaddem/equipe/add-equipe -H "Content-Type: application/json" -d '{"nomEquipe":"TEST","niveau":"JUNIOR"}'
                curl -X GET http://localhost:8089/kaddem/equipe/retrieve-all-equipes
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
            mail to: 'benjdidiahabib15@gmail.com',
                 subject: "Pipeline SUCCESS - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Good Job! The pipeline succeeded."
        }
        failure {
            echo 'Pipeline failed.'
            mail to: 'benjdidiahabib15@gmail.com',
                 subject: "Pipeline FAILURE - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Unfortunately, the pipeline failed. Check Jenkins for details."
        }
    }
}
