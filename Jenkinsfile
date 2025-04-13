pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'hamzambarki-4TWIN1-G5-pipeline', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh 'mvn deploy -X -DskipTests -DaltDeploymentRepository=nexus::default::http://localhost:8081/repository/kaddem-snapshots/ -Dusername=$NEXUS_USERNAME -Dpassword=$NEXUS_PASSWORD -Dmaven.wagon.httpclient.timeout=600 -e'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    withCredentials([usernamePassword(credentialsId: 'sonarqube-credentials', usernameVariable: 'SONAR_USERNAME', passwordVariable: 'SONAR_PASSWORD')]) {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.134:9000 -Dsonar.login=$SONAR_USERNAME -Dsonar.password=$SONAR_PASSWORD'
                    }
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t hamzambarki/kaddem:latest .'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_TOKEN')]) {
                    sh '''
                        echo "$DOCKER_TOKEN" | docker login -u "$DOCKER_USERNAME" --password-stdin
                        docker push hamzambarki/kaddem:latest
                    '''
                }
            }
        }
    }
}