pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'Java17'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = tool 'Maven'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        SONAR_TOKEN = credentials('sonar-token')
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
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
            mail to: 'your-email@example.com',
                 subject: "Jenkins Pipeline Failed: ${env.JOB_NAME}",
                 body: "Build ${env.BUILD_NUMBER} failed: ${env.BUILD_URL}"
        }
    }
}
