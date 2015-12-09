'use strict';

angular.module('ontimeApp').controller('ProjectRegionMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProjectRegionMilestone', 'ProjectRegion', 'Milestone',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ProjectRegionMilestone, ProjectRegion, Milestone) {

        $scope.projectRegionMilestone = entity;
        $scope.projectregions = ProjectRegion.query({filter: 'projectregionmilestone-is-null'});
        $q.all([$scope.projectRegionMilestone.$promise, $scope.projectregions.$promise]).then(function() {
            if (!$scope.projectRegionMilestone.projectRegionId) {
                return $q.reject();
            }
            return ProjectRegion.get({id : $scope.projectRegionMilestone.projectRegionId}).$promise;
        }).then(function(projectRegion) {
            $scope.projectregions.push(projectRegion);
        });
        $scope.milestones = Milestone.query({filter: 'projectregionmilestone-is-null'});
        $q.all([$scope.projectRegionMilestone.$promise, $scope.milestones.$promise]).then(function() {
            if (!$scope.projectRegionMilestone.milestoneId) {
                return $q.reject();
            }
            return Milestone.get({id : $scope.projectRegionMilestone.milestoneId}).$promise;
        }).then(function(milestone) {
            $scope.milestones.push(milestone);
        });
        $scope.load = function(id) {
            ProjectRegionMilestone.get({id : id}, function(result) {
                $scope.projectRegionMilestone = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectRegionMilestoneUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectRegionMilestone.id != null) {
                ProjectRegionMilestone.update($scope.projectRegionMilestone, onSaveSuccess, onSaveError);
            } else {
                ProjectRegionMilestone.save($scope.projectRegionMilestone, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
