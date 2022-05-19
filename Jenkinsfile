node {
    def selectServiceModules = "${project_name}".spilt(',')
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
    // 构建后进行代码检查
    stage('code check') {
        steps {
            script {
                scannerHome = tool 'sonar-scanner-4.2'
            }
            withSonarQubeEnv('sonar-server-7.7') {
                for(int i = 0; i < selectServiceModules.size(); i++) {
                    def serviceModule = selectServiceModules[i]
                    sh """
                        cd ${serviceModule}  // 进入二级项目，如zuul
                        ${scannerHome}/bin/sonar-scanner // 代码审查
                    """
                }

            }
        }
    }
}