pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'hamzambarki-4TWIN1-G5-pipeline', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.134:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t hamzambarki/kaddem:latest .'
                sh '''
                    echo "dckr_pat_o5Gm4QnItD3YACJRGoppKcCJfm4" | docker login -u "hamzambarki" --password-stdin
                    docker push hamzambarki/kaddem:latest
                '''
            }
        }
    }
}