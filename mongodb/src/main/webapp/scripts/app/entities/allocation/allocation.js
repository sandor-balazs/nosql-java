'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('allocation', {
                parent: 'entity',
                url: '/allocations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.allocation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/allocation/allocations.html',
                        controller: 'AllocationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('allocation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('allocation.detail', {
                parent: 'entity',
                url: '/allocation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.allocation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/allocation/allocation-detail.html',
                        controller: 'AllocationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('allocation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Allocation', function($stateParams, Allocation) {
                        return Allocation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('allocation.new', {
                parent: 'allocation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/allocation/allocation-dialog.html',
                        controller: 'AllocationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fte: null,
                                    year: null,
                                    month: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('allocation', null, { reload: true });
                    }, function() {
                        $state.go('allocation');
                    })
                }]
            })
            .state('allocation.edit', {
                parent: 'allocation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/allocation/allocation-dialog.html',
                        controller: 'AllocationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Allocation', function(Allocation) {
                                return Allocation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('allocation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('allocation.delete', {
                parent: 'allocation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/allocation/allocation-delete-dialog.html',
                        controller: 'AllocationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Allocation', function(Allocation) {
                                return Allocation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('allocation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
