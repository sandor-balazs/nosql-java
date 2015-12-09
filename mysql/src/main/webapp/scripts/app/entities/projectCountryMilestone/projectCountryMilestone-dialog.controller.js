'use strict';

angular.module('ontimeApp').controller('ProjectCountryMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProjectCountryMilestone', 'ProjectCountry', 'Milestone',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ProjectCountryMilestone, ProjectCountry, Milestone) {

        $scope.projectCountryMilestone = entity;
        $scope.projectcountrys = ProjectCountry.query({filter: 'projectcountrymilestone-is-null'});
        $q.all([$scope.projectCountryMilestone.$promise, $scope.projectcountrys.$promise]).then(function() {
            if (!$scope.projectCountryMilestone.projectCountryId) {
                return $q.reject();
            }
            return ProjectCountry.get({id : $scope.projectCountryMilestone.projectCountryId}).$promise;
        }).then(function(projectCountry) {
            $scope.projectcountrys.push(projectCountry);
        });
        $scope.milestones = Milestone.query({filter: 'projectcountrymilestone-is-null'});
        $q.all([$scope.projectCountryMilestone.$promise, $scope.milestones.$promise]).then(function() {
            if (!$scope.projectCountryMilestone.milestoneId) {
                return $q.reject();
            }
            return Milestone.get({id : $scope.projectCountryMilestone.milestoneId}).$promise;
        }).then(function(milestone) {
            $scope.milestones.push(milestone);
        });
        $scope.load = function(id) {
            ProjectCountryMilestone.get({id : id}, function(result) {
                $scope.projectCountryMilestone = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectCountryMilestoneUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectCountryMilestone.id != null) {
                ProjectCountryMilestone.update($scope.projectCountryMilestone, onSaveSuccess, onSaveError);
            } else {
                ProjectCountryMilestone.save($scope.projectCountryMilestone, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
