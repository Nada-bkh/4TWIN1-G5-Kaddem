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
        stage('Build JAR') {
            steps {
                sh 'echo "hamza" | sudo -S mvn clean package'
            }
        }
        stage('Build Docker Images') {
            steps {
                sh 'echo "hamza" | sudo -S docker-compose build'
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
                sh 'echo "hamza" | sudo -S docker-compose push'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "hamza" | sudo -S docker-compose up -d'
            }
        }
        stage('Verify Prometheus Metrics') {
            steps {
                script {
                    retry(5) {
                        sleep 10
                        def prometheus_url = 'http://192.168.33.134:9090/api/v1/query'
                        def query = 'up{job="spring-boot-application"}'
                        def response = sh(script: "curl -s '${prometheus_url}?query=${URLEncoder.encode(query, "UTF-8")}'", returnStdout: true)
                        echo "Prometheus Response: ${response}"
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'echo "hamza" | sudo -S mvn sonar:sonar -Dsonar.host.url=http://192.168.33.134:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }
        }
    }
}