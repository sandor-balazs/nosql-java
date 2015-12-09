'use strict';

angular.module('ontimeApp')
	.controller('FundingDeleteController', function($scope, $uibModalInstance, entity, Funding) {

        $scope.funding = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Funding.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
