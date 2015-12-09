'use strict';

angular.module('ontimeApp')
	.controller('ProjectCountryDeleteController', function($scope, $uibModalInstance, entity, ProjectCountry) {

        $scope.projectCountry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectCountry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
