'use strict';

angular.module('ontimeApp').controller('ProjectMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectMilestone',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectMilestone) {

        $scope.projectMilestone = entity;
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
