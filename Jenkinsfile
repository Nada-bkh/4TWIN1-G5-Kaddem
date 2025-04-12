pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Build') {
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


        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès.'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}

