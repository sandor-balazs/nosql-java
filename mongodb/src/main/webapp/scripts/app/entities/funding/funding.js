'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('funding', {
                parent: 'entity',
                url: '/fundings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.funding.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funding/fundings.html',
                        controller: 'FundingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('funding');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('funding.detail', {
                parent: 'entity',
                url: '/funding/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.funding.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funding/funding-detail.html',
                        controller: 'FundingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('funding');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Funding', function($stateParams, Funding) {
                        return Funding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('funding.new', {
                parent: 'funding',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funding/funding-dialog.html',
                        controller: 'FundingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('funding', null, { reload: true });
                    }, function() {
                        $state.go('funding');
                    })
                }]
            })
            .state('funding.edit', {
                parent: 'funding',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funding/funding-dialog.html',
                        controller: 'FundingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Funding', function(Funding) {
                                return Funding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('funding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('funding.delete', {
                parent: 'funding',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funding/funding-delete-dialog.html',
                        controller: 'FundingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Funding', function(Funding) {
                                return Funding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('funding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
