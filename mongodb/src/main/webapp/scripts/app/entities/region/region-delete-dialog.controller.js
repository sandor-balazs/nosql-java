'use strict';

angular.module('ontimeApp')
	.controller('RegionDeleteController', function($scope, $uibModalInstance, entity, Region) {

        $scope.region = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Region.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
