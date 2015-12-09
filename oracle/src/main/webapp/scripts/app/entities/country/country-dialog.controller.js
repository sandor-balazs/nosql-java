'use strict';

angular.module('ontimeApp').controller('CountryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Country', 'Location', 'Region',
        function($scope, $stateParams, $uibModalInstance, entity, Country, Location, Region) {

        $scope.country = entity;
        $scope.locations = Location.query();
        $scope.regions = Region.query();
        $scope.load = function(id) {
            Country.get({id : id}, function(result) {
                $scope.country = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:countryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.country.id != null) {
                Country.update($scope.country, onSaveSuccess, onSaveError);
            } else {
                Country.save($scope.country, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
