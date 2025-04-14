pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        NEXUS_REPO = 'http://10.0.2.15:8081/repository/maven-snapshots/'
        SONARQUBE = 'MelekKaddem'
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

         stage('Publish to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh """
                        mvn deploy -DskipTests \\
                        -DaltDeploymentRepository=nexus::default::${NEXUS_REPO} \\
                        -Dnexus.user=\$NEXUS_USER \\
                        -Dnexus.password=\$NEXUS_PASS
                    """
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
