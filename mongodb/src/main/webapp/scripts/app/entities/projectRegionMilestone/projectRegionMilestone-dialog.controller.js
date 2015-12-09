'use strict';

angular.module('ontimeApp').controller('ProjectRegionMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectRegionMilestone',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectRegionMilestone) {

        $scope.projectRegionMilestone = entity;
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
