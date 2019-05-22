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
        stage('Unit test') {
            steps {
                junit '**//*target/surefire-reports/TEST-*.xml'
                archive 'target*//*.jar'
            }
        }
    }
}