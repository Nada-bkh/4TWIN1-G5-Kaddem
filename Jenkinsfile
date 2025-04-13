pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Nada-bkh/4TWIN1-G5-Kaddem.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.134:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -X -DskipTests -DaltDeploymentRepository=nexus::default::http://localhost:8081/repository/kaddem-snapshots/ -Dusername=admin -Dpassword=a5670a5129054b41bf5ca1fb76782faf -Dmaven.wagon.httpclient.timeout=600'
            }
        }
        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t hamzambarki/kaddem:latest .'
                sh '''
                    echo "dckr_pat_Ll-8NNngdCrB-yyqr_izyxzlOrc" | docker login -u "hamzambarki" --password-stdin
                    docker push hamzambarki/kaddem:latest
                '''
            }
        }
    }
}