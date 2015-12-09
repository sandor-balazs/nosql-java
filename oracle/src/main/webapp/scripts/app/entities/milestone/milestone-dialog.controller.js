'use strict';

angular.module('ontimeApp').controller('MilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Milestone',
        function($scope, $stateParams, $uibModalInstance, entity, Milestone) {

        $scope.milestone = entity;
        $scope.load = function(id) {
            Milestone.get({id : id}, function(result) {
                $scope.milestone = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:milestoneUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.milestone.id != null) {
                Milestone.update($scope.milestone, onSaveSuccess, onSaveError);
            } else {
                Milestone.save($scope.milestone, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
