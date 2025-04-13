pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // 'SonarQube' should match your SonarQube server name in Jenkins
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests --settings settings.xml'
            }
        }
        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t hamzambarki/kaddem:latest .'
                sh '''
                    echo "your_docker_password" | docker login -u "your_docker_username" --password-stdin
                    docker push hamzambarki/kaddem:latest
                '''
            }
        }
    }
}