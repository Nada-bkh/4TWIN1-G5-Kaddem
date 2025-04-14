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

        stage('Run Prometheus') {
            steps {
                script {
                    sh '''
                        docker start prometheus || echo "Prometheus already running"
                    '''
                }
            }
        }

        stage('Run Grafana') {
            steps {
                script {
                    sh '''
                        docker start grafana || echo "Grafana already running"
                    '''
                }
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
