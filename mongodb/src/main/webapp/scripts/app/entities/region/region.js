'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('region', {
                parent: 'entity',
                url: '/regions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.region.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/regions.html',
                        controller: 'RegionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('region');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('region.detail', {
                parent: 'entity',
                url: '/region/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.region.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/region-detail.html',
                        controller: 'RegionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('region');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Region', function($stateParams, Region) {
                        return Region.get({id : $stateParams.id});
                    }]
                }
            })
            .state('region.new', {
                parent: 'region',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/region/region-dialog.html',
                        controller: 'RegionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('region', null, { reload: true });
                    }, function() {
                        $state.go('region');
                    })
                }]
            })
            .state('region.edit', {
                parent: 'region',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/region/region-dialog.html',
                        controller: 'RegionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Region', function(Region) {
                                return Region.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('region', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('region.delete', {
                parent: 'region',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/region/region-delete-dialog.html',
                        controller: 'RegionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Region', function(Region) {
                                return Region.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('region', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
