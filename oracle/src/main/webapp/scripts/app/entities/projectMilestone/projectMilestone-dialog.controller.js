'use strict';

angular.module('ontimeApp').controller('ProjectMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProjectMilestone', 'Project', 'Milestone',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ProjectMilestone, Project, Milestone) {

        $scope.projectMilestone = entity;
        $scope.projects = Project.query({filter: 'projectmilestone-is-null'});
        $q.all([$scope.projectMilestone.$promise, $scope.projects.$promise]).then(function() {
            if (!$scope.projectMilestone.projectId) {
                return $q.reject();
            }
            return Project.get({id : $scope.projectMilestone.projectId}).$promise;
        }).then(function(project) {
            $scope.projects.push(project);
        });
        $scope.milestones = Milestone.query({filter: 'projectmilestone-is-null'});
        $q.all([$scope.projectMilestone.$promise, $scope.milestones.$promise]).then(function() {
            if (!$scope.projectMilestone.milestoneId) {
                return $q.reject();
            }
            return Milestone.get({id : $scope.projectMilestone.milestoneId}).$promise;
        }).then(function(milestone) {
            $scope.milestones.push(milestone);
        });
        $scope.load = function(id) {
            ProjectMilestone.get({id : id}, function(result) {
                $scope.projectMilestone = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectMilestoneUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectMilestone.id != null) {
                ProjectMilestone.update($scope.projectMilestone, onSaveSuccess, onSaveError);
            } else {
                ProjectMilestone.save($scope.projectMilestone, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
