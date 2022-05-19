pipeline {
    agent any

    stages {
        // 拉取代码
        stage('pull code') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'a7cb7f55-c962-427f-a040-4c8c92219abc', url: 'git@github.com:Dreamer-07/guli_parent.git']]])
            }
        }
        // 构建项目并上传的服务器上
        stage('project build') {
            steps {
                // 配置nexus私服
                sh 'mvn clean deploy'
                // 未配置nexus私服
                // sh 'mvn clean package'
            }
        }
    }
}