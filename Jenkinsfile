pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "benjdidiahabib-4twin1-g5-kaddem"
        IMAGE_TAG = "latest"
        NEXUS_URL = "localhost:5001"
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
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('Deploy JAR to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-docker-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh '''
                        docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                        docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${NEXUS_URL}/${IMAGE_NAME}:${IMAGE_TAG}
                        echo $NEXUS_PASS | docker login ${NEXUS_URL} -u $NEXUS_USER --password-stdin
                        docker push ${NEXUS_URL}/${IMAGE_NAME}:${IMAGE_TAG}
                    '''
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Monitoring with Prometheus & Grafana') {
            steps {
                sh '''
                    docker start prometheus || echo "Prometheus already running"
                    docker start grafana || echo "Grafana already running"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès.'
            mail to: 'benjdidiahabib15@gmail.com',
                 subject: "Pipeline SUCCESS - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Good Job! The pipeline succeeded."
        }
        failure {
            echo 'Le pipeline a échoué.'
            mail to: 'benjdidiahabib15@gmail.com',
                 subject: "Pipeline FAILURE - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Unfortunately, the pipeline failed. Check Jenkins for details."
        }
    }
}
