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
            // 	If the maven build succeeded, archive the JUnit test reports
            steps {
                junit 'target/surefire-reports/**/*.xml'
            }
        }

        stage('Deploy') {
            steps {
                echo 'deploy project'
                // copy bank-account-bank-0.0.1-SNAPSHOT.jar in server
                // run app :  java -jar target/bank-account-bank-0.0.1-SNAPSHOT.jar
            }
        }
    }
}