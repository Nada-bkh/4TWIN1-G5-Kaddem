pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "benjdidiahabib-4twin1-g5-kaddem"
        IMAGE_TAG = "latest"
        NEXUS_URL = "localhost:5001" // Nexus Docker Repo runs on 5001
    }

    stages {
        stage('Build et Test') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN}"
                }
            }
        }

        stage('Deploy JAR to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('Docker Build & Push to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-docker-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh '''
                        docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                        docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${NEXUS_URL}/${IMAGE_NAME}:${IMAGE_TAG}
                        echo "${NEXUS_PASS}" | docker login ${NEXUS_URL} -u "${NEXUS_USER}" --password-stdin
                        docker push ${NEXUS_URL}/${IMAGE_NAME}:${IMAGE_TAG}
                    '''
                }
            }
        }

        stage('Run Prometheus') {
            steps {
                sh '''
                    docker start prometheus || echo "Prometheus already running"
                '''
            }
        }

        stage('Run Grafana') {
            steps {
                sh '''
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
                 body: "Good Job! Jenkins Pipeline [${env.JOB_NAME}] build number #${env.BUILD_NUMBER} succeeded 🎉"
        }

        failure {
            echo 'Le pipeline a échoué.'
            mail to: 'benjdidiahabib15@gmail.com',
                 subject: "Pipeline FAILURE - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Unfortunately, Jenkins Pipeline [${env.JOB_NAME}] build number #${env.BUILD_NUMBER} failed 🚨. Check Jenkins for more details."
        }
    }
}
