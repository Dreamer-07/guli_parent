node {
    def selectedProjects = "${project_name}".split(',')
    // docker harbor 仓库地址
    def harborUrl = "192.168.102.132"
    // docker harbor project
    def harborProjectName = "guli"

    // 拉取代码
    stage('pull code') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'a7cb7f55-c962-427f-a040-4c8c92219abc', url: 'git@github.com:Dreamer-07/guli_parent.git']]])
    }
    // 构建项目并上传的服务器上
    stage('project build') {
        // (无 nexus) 安装公共依赖
        // sh 'mvn -f guli-common clean install'
        // (有 nexus) 安装公共依赖
        sh 'mvn -f guli-common clean deploy'
        for (int i = 0; i < selectedProjects.size(); i++) {
            def currentProject = selectedProjects[i]
            // (无 nexus) 项目打包
            // sh 'mvn -f ${currentProject} clean install'
            // (有 nexus) 项目打包
            sh "mvn -f ${currentProject} clean deploy"
        }
    }
    // 构建后进行代码检查
    stage('code check') {
        def scannerHome = tool 'sonar-scanner-4.2'
        withSonarQubeEnv('sonar-server-7.7') {
            sh """
                ${scannerHome}/bin/sonar-scanner
            """
        }
    }
    // 生成镜像
    stage('docker project deploy') {
        for (int i = 0; i < selectedProjects.size(); i++) {

            def currentProject = selectedProjects[i]
            // 使用 maven 构建本地镜像

            sh "mvn -f ${currentProject} dockerfile:build"

            if (currentProject.contains('/')) {
                currentProject = currentProject.split('/')[1]
            }

            // 给 maven 打标签
            sh "docker tag ${currentProject}:latest ${harborUrl}/${harborProjectName}/${currentProject}:latest"

            // 上传到 docker harbor
            withCredentials([usernamePassword(credentialsId: '25e54fc4-3711-41e6-b85c-14317c9dc2fc', passwordVariable: 'password', usernameVariable: 'username')]) {
                // 登录
                sh "docker login -u ${username} -p ${password} ${harborUrl}"
                // 上传镜像
                sh "docker push ${harborUrl}/${harborProjectName}/${currentProject}:latest"
            }

            // 删除本地镜像
            sh "docker rmi -f ${currentProject}"
            sh "docker rmi -f ${harborUrl}/${harborProjectName}/${currentProject}:latest"

            // 发布命令到远程服务器
            sshPublisher(publishers: [sshPublisherDesc(configName: 'jenkins-prod-1', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/opt/jenkins_shell/deploy.sh $harborUrl $harborProjectName $currentProject", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
        }
    }
}