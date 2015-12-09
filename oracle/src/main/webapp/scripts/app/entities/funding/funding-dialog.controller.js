'use strict';

angular.module('ontimeApp').controller('FundingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Funding',
        function($scope, $stateParams, $uibModalInstance, entity, Funding) {

        $scope.funding = entity;
        $scope.load = function(id) {
            Funding.get({id : id}, function(result) {
                $scope.funding = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:fundingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.funding.id != null) {
                Funding.update($scope.funding, onSaveSuccess, onSaveError);
            } else {
                Funding.save($scope.funding, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
