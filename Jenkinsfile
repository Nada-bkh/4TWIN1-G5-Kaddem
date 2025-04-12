pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t myapp:latest .'
                sh 'docker tag myapp:latest myrepo/myapp:latest'
                sh 'docker push myrepo/myapp:latest'
            }
        }
    }
}
