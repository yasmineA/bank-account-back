pipeline {
    agent any
        tools {
            maven 'M3'
            jdk 'JAVA_8'
        }
    stages {
        stage('Build') {
            // cleanly build Java application (without running any tests).
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'echo deploy project'
                // copy bank-account-bank-0.0.1-SNAPSHOT.jar in server
                // run app :  java -jar target/bank-account-bank-0.0.1-SNAPSHOT.jar
            }
        }
    }
}