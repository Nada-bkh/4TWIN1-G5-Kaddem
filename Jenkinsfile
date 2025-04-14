pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        NEXUS_REPO = 'http://10.0.2.15:8081/repository/maven-snapshots/'
        SONARQUBE = 'MelekKaddem'
                NEXUS_URL = 'http://10.0.2.15:8081'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'MelekJdidi-4TWIN1-G5', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
                    steps {
                        script {
                            def tag = "kaddem-app:${env.BUILD_NUMBER}"
                            sh "docker build -t ${tag} ."
                        }
                    }
                }

        stage('Build and Deploy to Nexus') {
                  steps {
                      withMaven(globalMavenSettingsConfig: '2482651b-f6c0-46f7-b920-9ea07832b08f') {
                          sh '''
                              mvn clean package deploy \
                              -DaltDeploymentRepository=4TWIN1-G5-Kaddem::default::${NEXUS_URL}/repository/4TWIN1-G5-Kaddem/ \
                              -X
                          '''
                      }
                  }
              }


        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        docker tag kaddem-app:${env.BUILD_NUMBER} melekjdidi/kaddem:0.0.1
                        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                        docker push melekjdidi/kaddem:0.0.1
                    """
                }
            }
        }

    }
}
