pipeline {
    agent any
        tools {
            maven 'M3'
            jdk 'JAVA_8'
        }
    stages {
        stage('Build') {
            // Call mvn, the version configured by the tools section will be first on the path
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
        }
        stage('Test') {
            // 	If the maven build succeeded, archive the JUnit test reports
            steps {
                junit 'target/surefire-reports/**/*.xml'
            }
        }
    }
}