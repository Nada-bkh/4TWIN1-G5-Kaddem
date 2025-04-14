pipeline {
    agent any

    tools {
        maven 'Maven'
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
