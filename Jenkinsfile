pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                sh '''
                    git clone -b hamzambarki-4TWIN1-G5-pipeline --depth 1 https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git .
                    git fetch --unshallow
                    git checkout hamzambarki-4TWIN1-G5-pipeline
                    ls -la
                '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
                sh 'ls -la target/'
            }
        }


        stage('Build Docker Images') {
            steps {
                sh 'docker-compose build'
            }
        }
        stage('Login to DockerHub') {
            steps {
                sh '''
                    echo "dckr_pat_o5Gm4QnItD3YACJRGoppKcCJfm4" | docker login -u "hamzambarki" --password-stdin
                '''
            }
        }
        stage('Push Docker Image') {
            steps {
                sh 'docker-compose push'
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
               stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.134:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }
        }
    }
}