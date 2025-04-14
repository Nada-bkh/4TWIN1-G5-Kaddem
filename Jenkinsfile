pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
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

/*         stage('Deploy to Nexus') {
            steps {
                sh 'mvn clean deploy -DskipTests '
            }
        } */

        stage('Deploy to Nexus') {
          steps {
            configFileProvider([configFile(fileId: 'nexus-settings', variable: 'MAVEN_SETTINGS')]) {
              sh 'mvn clean deploy -DskipTests --settings $MAVEN_SETTINGS'
            }
          }
        }

    }
}