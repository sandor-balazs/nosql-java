'use strict';

angular.module('ontimeApp').controller('ProjectCountryMilestoneDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectCountryMilestone',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectCountryMilestone) {

        $scope.projectCountryMilestone = entity;
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
