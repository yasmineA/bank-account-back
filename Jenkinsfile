pipeline {
    agent any
        tools {
            maven 'M3'
            jdk 'JAVA_8'
        }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}