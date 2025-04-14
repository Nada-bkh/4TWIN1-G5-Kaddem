pipeline {
    agent any

    tools {
        maven 'Maven3' // nom de l'outil Maven défini dans Jenkins
    }

    environment {
        SONARQUBE = 'MelekKaddem'
        MAVEN_HOME = tool 'Maven' // Assure-toi que ce nom est exact dans Jenkins
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

        stage('Build & Deploy to Nexus') {
            steps {
                withMaven(
                    maven: 'Maven', // nom exact configuré dans Jenkins
                    mavenSettingsConfig: 'nexus-settings' // ID du settings.xml Nexus dans "Managed Files"
                ) {
                    sh 'mvn clean deploy -DskipTests'
                }
            }
        }
    }
}
