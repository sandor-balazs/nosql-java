'use strict';

angular.module('ontimeApp')
	.controller('LocationDeleteController', function($scope, $uibModalInstance, entity, Location) {

        $scope.location = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Location.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
